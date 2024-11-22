<template>
  <v-card
    :loading="isUpdating"
    class="mx-auto"
    color="white"
    max-width="480"
    outlined
  >
    <template v-slot:loader="{ isActive }">
      <v-progress-linear
        :active="isActive"
        color="green-lighten-3"
        height="4"
        indeterminate
      ></v-progress-linear>
    </template>

    <v-row class="pa-5">
      <v-col cols="12" class="text-center">
        <h3 class="text-h5" style="font-size: 36px; font-weight: 600">
          Cadastro de Agendas
        </h3>
      </v-col>
    </v-row>

    <v-form>
      <v-container>
        <v-row dense>
          <v-col cols="12">
            <v-autocomplete
              v-model="selectedDoctor"
              :items="doctors"
              item-title="nome"
              item-value="id"
              label="Selecione o médico"
              color="deep-purple lighten-2"
              outlined
              dense
              :disabled="isUpdating"
              class="mb-4"
            >
              <template v-slot:item="{ item, props }">
                <v-list-item v-bind="props">
                  <v-list-item-content>
                    <v-list-item-title>{{ item.nome }}</v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
              </template>
            </v-autocomplete>
          </v-col>

          <v-col cols="12" class="d-flex">
            <v-row>
              <v-col cols="6">
                <v-checkbox
                  v-model="selectedDays"
                  label="Segunda-feira"
                  value="Segunda-feira"
                ></v-checkbox>
                <v-checkbox
                  v-model="selectedDays"
                  label="Terça-feira"
                  value="Terça-feira"
                ></v-checkbox>
                <v-checkbox
                  v-model="selectedDays"
                  label="Quarta-feira"
                  value="Quarta-feira"
                ></v-checkbox>
              </v-col>
              <v-col cols="6">
                <v-checkbox
                  v-model="selectedDays"
                  label="Quinta-feira"
                  value="Quinta-feira"
                ></v-checkbox>
                <v-checkbox
                  v-model="selectedDays"
                  label="Sexta-feira"
                  value="Sexta-feira"
                ></v-checkbox>
              </v-col>
            </v-row>
          </v-col>

          <v-col cols="12">
            <v-autocomplete
              v-model="selectedTime"
              :items="availableTimes"
              item-title="label"
              item-value="value"
              label="Selecione os horários disponíveis"
              multiple
              chips
              closable-chips
              outlined
              color="deep-purple lighten-2"
              :disabled="isUpdating"
              class="mb-4"
            >
              <template v-slot:item="{ item, props }">
                <v-list-item v-bind="props">
                  <v-list-item-content>
                    <v-list-item-title>{{ item.label }}</v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
              </template>
            </v-autocomplete>
          </v-col>

          <v-col cols="12">
            <v-switch
              v-model="isActiveAgenda"
              label="Agenda Ativa"
              color="deep-purple lighten-2"
              class="mb-4"
            ></v-switch>
          </v-col>
        </v-row>
      </v-container>
    </v-form>

    <v-card-actions>
      <v-btn
        color="deep-purple lighten-2"
        text
        block
        large
        @click="handleRegister"
        class="mb-4"
      >
        Cadastrar Agenda
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      isUpdating: false,
      selectedDoctor: null,
      selectedDays: [],
      selectedTime: [],
      isActiveAgenda: true,
      doctors: [],
      availableTimes: [
        { value: '07:00', label: '7:00 AM' },
        { value: '09:00', label: '9:00 AM' },
        { value: '11:00', label: '11:00 AM' },
        { value: '13:00', label: '1:00 PM' },
        { value: '15:00', label: '3:00 PM' },
        { value: '17:00', label: '5:00 PM' },
        { value: '19:00', label: '7:00 PM' },
        { value: '21:00', label: '9:00 PM' },
      ],
    };
  },
  mounted() {
    this.fetchDoctors();
  },
  methods: {
    async fetchDoctors() {
      try {
        const response = await axios.get('http://localhost:8080/medicos');
        this.doctors = response.data.filter(doctor => doctor.status);
      } catch (error) {
        console.error('Erro ao buscar médicos:', error);
      }
    },

    async handleRegister() {
      if (!this.selectedDoctor || this.selectedDays.length === 0 || this.selectedTime.length === 0) {
        alert('Por favor, preencha todos os campos obrigatórios');
        return;
      }

      this.isUpdating = true;

      try {
        const response = await axios.get('http://localhost:8080/agendas');
        const existingAgendas = response.data || [];

        console.log('Agendas existentes:', existingAgendas);

        for (const day of this.selectedDays) {
          const agendaData = {
            medico: {
              id: this.selectedDoctor,
            },
            diasSemana: day,
            horariosDisponiveis: this.selectedTime.join(', '),
            status: this.isActiveAgenda,
          };

          const existingAgenda = existingAgendas.find(
            agenda =>
              agenda.medico.id === this.selectedDoctor &&
              agenda.diasSemana === day
          );

          if (existingAgenda) {
            console.log(`Atualizando agenda para o dia: ${day}`);
            try {
              await axios.put(
                `http://localhost:8080/agendas/${existingAgenda.id}`,
                agendaData
              );
              console.log('Agenda atualizada com sucesso:', day);
              alert(`Agenda atualizada com sucesso para o dia ${day}`);
            } catch (error) {
              console.error('Erro ao atualizar agenda para o dia', day, error);
              alert(`Erro ao atualizar agenda para o dia ${day}`);
            }
          } else {
            console.log(`Cadastrando nova agenda para o dia: ${day}`);
            try {
              await axios.post('http://localhost:8080/agendas', agendaData);
              console.log('Agenda cadastrada com sucesso para o dia:', day);
              alert(`Agenda cadastrada com sucesso para o dia ${day}`);
            } catch (error) {
              console.error('Erro ao cadastrar agenda para o dia', day, error);
              alert(`Erro ao cadastrar agenda para o dia ${day}`);
            }
          }
        }
      } catch (error) {
        console.error('Erro ao buscar agendas:', error);
        alert('Erro ao buscar agendas.');
      } finally {
        this.isUpdating = false;
      }
    },
  },
};
</script>

<style scoped>
  .v-card {
    border: 1px solid #e0e0e0;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  }

  .v-autocomplete .v-list-item {
    cursor: pointer;
  }

  .v-autocomplete .v-chip {
    margin: 4px;
  }

  .v-btn {
    font-weight: 600;
  }
</style>
