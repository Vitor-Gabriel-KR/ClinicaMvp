<template>
  <v-container>
    <v-row>
      <v-col
        v-for="medico in medicosAtivos"
        :key="medico.id"
        cols="12" sm="6" md="4"
      >
        <v-card
          :disabled="loading"
          :loading="loading"
          class="mx-auto my-8"
          max-width="300"
        >
          <template v-slot:loader="{ isActive }">
            <v-progress-linear
              :active="isActive"
              color="deep-purple"
              height="3"
              indeterminate
            ></v-progress-linear>
          </template>

          <v-img
            height="264"
            src="https://img.freepik.com/vetores-premium/avatar-redondo-do-medico-avatar-plano-de-medicina-com-medico-masculino-equipe-clinica-medica_625536-2178.jpg"
            cover
          ></v-img>

          <v-card-item>
            <v-card-title style="font-size: 16px;">{{ medico.nome }}</v-card-title>
            <v-card-subtitle style="font-size: 12px;">
              <span class="me-1">{{ medico.estado }}</span>
              <v-icon size="small">
                <img
                  src="https://upload.wikimedia.org/wikipedia/en/0/05/Flag_of_Brazil.svg"
                  alt="Bandeira do Brasil"
                  style="width: 16px; height: 16px;"
                />
              </v-icon>
            </v-card-subtitle>
          </v-card-item>

          <v-divider class="mx-3 mb-1"></v-divider>

          <v-card-title style="font-size: 16px;">Disponibilidade</v-card-title>

          <div class="px-3 mb-2">
            <v-select
              v-model="diaSelecionado[medico.id]"
              :items="diasUteis[medico.id]"
              label="Dia da Semana"
              chips
              style="font-size: 12px;"
              @change="updateHorarios(medico.id)"
            ></v-select>
          </div>

          <div class="px-3 mb-2">
            <v-chip-group
              v-model="horariosSelecionados[medico.id]"
              selected-class="bg-deep-purple-lighten-2"
            >
              <v-chip
                v-for="hora in (horariosDisponiveis[medico.id] && horariosDisponiveis[medico.id][diaSelecionado[medico.id]] || [])"
                :key="hora"
                :value="hora"
                style="font-size: 12px;"
              >
                {{ hora }}
              </v-chip>
            </v-chip-group>
          </div>

          <v-card-actions>
            <v-btn
              color="deep-purple-lighten-2"
              text
              block
              border
              style="font-size: 14px;"
              @click="openDialog(medico)"
            >
              Reservar
            </v-btn>
          </v-card-actions>

          <v-dialog v-model="dialog" max-width="400">
            <v-card>
              <v-card-title style="font-size: 16px;">Escolha um paciente</v-card-title>
              <v-card-text>
                <v-autocomplete
                  v-model="paciente_id"
                  :items="pacientes"
                  item-title="nome"
                  item-value="id"
                  label="Selecione o paciente"
                  color="deep-purple-lighten-2"
                  outlined
                  :disabled="loading"
                  style="font-size: 14px;"
                >
                  <template v-slot:item="{ item, props }">
                    <v-list-item v-bind="props">
                      <v-list-item-content>
                        <v-list-item-title>{{ item.nome }}</v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                  </template>
                </v-autocomplete>

                <v-select
                  v-model="status"
                  :items="statusOptions"
                  label="Status da Consulta"
                  class="mt-3"
                  style="font-size: 14px;"
                ></v-select>
              </v-card-text>
              <v-card-actions>
                <v-btn
                  color="deep-purple-lighten-2"
                  text
                  @click="validateAndSubmit"
                  style="font-size: 14px;"
                >
                  Confirmar
                </v-btn>
                <v-btn text @click="closeDialog" style="font-size: 14px;">Cancelar</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      loading: false,
      dialog: false,
      paciente_id: null,
      status: "Pendente",
      statusOptions: ["Confirmada", "Pendente", "Cancelada"],
      diasUteis: {},
      diaSelecionado: {},
      horariosSelecionados: {},
      horariosDisponiveis: {},
      medicos: [],
      medicosAtivos: [],
    };
  },
  mounted() {
    this.fetchMedicos();
    this.fetchPacientes();
    this.fetchAgendas();
  },
  methods: {
    async fetchMedicos() {
      try {
        const response = await axios.get('http://localhost:8080/medicos');
        this.medicos = response.data;
        this.medicosAtivos = this.medicos.filter(medico => medico.status);
      } catch (error) {
        console.error("Erro ao buscar médicos:", error);
      }
    },
    async fetchPacientes() {
      try {
        const response = await axios.get('http://localhost:8080/pacientes');
        this.pacientes = response.data;
      } catch (error) {
        console.error("Erro ao buscar pacientes:", error);
      }
    },
    async fetchAgendas() {
      try {
        const response = await axios.get('http://localhost:8080/agendas');
        response.data.forEach(agenda => {
          if (agenda.medico.status) {
            const medicoId = agenda.medico.id;
            
            if (!this.horariosDisponiveis[medicoId]) {
              this.horariosDisponiveis[medicoId] = {};
            }
            if (!this.diasUteis[medicoId]) {
              this.diasUteis[medicoId] = [];
            }

            const dias = agenda.diasSemana.split(', ');
            dias.forEach(dia => {
              if (!this.horariosDisponiveis[medicoId][dia]) {
                this.horariosDisponiveis[medicoId][dia] = agenda.horariosDisponiveis.split(', ');
              }
              if (!this.diasUteis[medicoId].includes(dia)) {
                this.diasUteis[medicoId].push(dia);
              }
            });
          }
        });
      } catch (error) {
        console.error("Erro ao buscar agendas:", error);
      }
    },
    updateHorarios(medicoId) {
      const dia = this.diaSelecionado[medicoId];
      if (dia) {
        this.horariosSelecionados[medicoId] = this.horariosDisponiveis[medicoId][dia] || [];
      }
    },
    openDialog(medico) {
      this.dialog = true;
      this.medicoSelecionado = medico;
    },
    closeDialog() {
      this.dialog = false;
    },
    async validateAndSubmit() {
      if (!this.paciente_id || !this.diaSelecionado[this.medicoSelecionado.id] || !this.horariosSelecionados[this.medicoSelecionado.id]) {
        alert("Por favor, preencha todos os campos obrigatórios.");
        return;
      }

      const dia = this.diaSelecionado[this.medicoSelecionado.id];
    const horario = this.horariosSelecionados[this.medicoSelecionado.id];

    const dataHora = horario;

    const consulta = {
      paciente: { id: this.paciente_id },
      agenda: { id: this.medicoSelecionado.id },
      dia: dia,
      dataHora: dataHora,
      status: this.status
    };


      console.log(consulta);

      this.loading = true;
      try {
        await axios.post('http://localhost:8080/consultas', consulta);
        this.loading = false;
        alert("Consulta cadastrada com sucesso!");
        this.dialog = false;
      } catch (error) {
        this.loading = false;
        console.error("Erro ao cadastrar consulta:", error);
        alert("Erro ao cadastrar consulta.");
      }
    }
  }
};
</script>

<style scoped>
.v-chip {
  height: 24px !important;
}
</style>
