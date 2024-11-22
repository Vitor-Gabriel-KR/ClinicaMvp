<template>
  <v-container fluid class="d-flex justify-center align-center" style="min-height: 100vh;">
    <v-row justify="center" align="center" class="w-100">
      <v-col cols="12" sm="8" md="6">
        <v-card class="pa-4">
          <h1 class="text-center mb-4">Cadastro de Médicos</h1>
          <form @submit.prevent="validateAndSubmit">
            <v-text-field
              v-model="state.name"
              :counter="50"
              label="Nome"
              required
              :class="{'error--text': v$.name.$error}"
              @blur="v$.name.$touch"
              @input="v$.name.$touch"
            ></v-text-field>

            <v-text-field
              v-model="state.conselho"
              :counter="15"
              label="Conselho (CRM)"
              required
              :class="{'error--text': v$.conselho.$error}"
              @blur="v$.conselho.$touch"
              @input="v$.conselho.$touch"
            ></v-text-field>

            <v-select
              v-model="state.estado"
              :items="estados"
              label="Estado"
              required
              :class="{'error--text': v$.estado.$error}"
              @blur="v$.estado.$touch"
              @change="v$.estado.$touch"
            ></v-select>

            <v-select
              v-model="state.status"
              :items="statusOptions"
              label="Status"
              required
              :class="{'error--text': v$.status.$error}"
              @blur="v$.status.$touch"
              @change="v$.status.$touch"
            ></v-select>

            <div class="d-flex justify-end mt-4">
              <v-btn 
                :style="{ backgroundColor: isFormValid ? '#8BC34A' : '#f5f5f5' }" 
                class="me-4" 
                @click="validateAndSubmit" 
                large
              >
                Cadastrar
              </v-btn>
              <v-btn @click="clear" large>Limpar</v-btn>
            </div>
          </form>
        </v-card>
      </v-col>
    </v-row>

    <v-alert
      v-if="formInvalid"
      type="error"
      class="position-absolute top-0 right-0 m-4"
      :style="{ zIndex: 9999 }"
    >
      Por favor, preencha todos os campos obrigatórios.
    </v-alert>
  </v-container>
</template>

<script setup>
import { reactive, ref, computed } from 'vue';
import { useVuelidate } from '@vuelidate/core';
import { required } from '@vuelidate/validators';
import axios from 'axios';

const estados = [
  'AC', 'AL', 'AP', 'AM', 'BA', 'CE', 'DF', 'ES', 'GO', 'MA', 'MT', 'MS', 'MG', 'PA', 'PB', 'PR', 'PE', 'PI',
  'RJ', 'RN', 'RS', 'RO', 'RR', 'SC', 'SP', 'SE', 'TO',
];

const statusOptions = ['Ativo', 'Inativo'];

const initialState = {
  name: '',
  conselho: '',
  estado: null,
  status: null,
};

const state = reactive({ ...initialState });
const formInvalid = ref(false);
const formSubmitted = ref(false);

const rules = {
  name: { required },
  conselho: { required },
  estado: { required },
  status: { required },
};

const v$ = useVuelidate(rules, state);

const isFormValid = computed(() => v$.value.$pending === false && v$.value.$invalid === false);

function clear() {
  v$.value.$reset();
  for (const [key, value] of Object.entries(initialState)) {
    state[key] = value;
  }
}

async function validateAndSubmit() {
  const isValid = await v$.value.$validate();
  formInvalid.value = !isValid;

  if (isValid) {
    try {
      const payload = {
        nome: state.name,
        conselho: state.conselho,
        estado: state.estado,
        status: state.status === 'Ativo',
      };
      console.log(payload)

      const response = await axios.post('http://localhost:8080/medicos', payload);
      console.log('Médico cadastrado com sucesso:', response.data);

      formSubmitted.value = true;
      clear();

      setTimeout(() => {
        formSubmitted.value = false;
      }, 5000);
    } catch (error) {
      console.error('Erro ao cadastrar o médico:', error.response || error.message);
      alert('Ocorreu um erro ao cadastrar o médico. Verifique os dados e tente novamente.');
    }
  } else {
    console.log('Formulário inválido.');
    setTimeout(() => {
      formInvalid.value = false;
    }, 5000);
  }
}
</script>

<style scoped>
.v-container {
  min-height: 100vh;
  padding-right: 30%;
}

.v-card {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}

h1 {
  font-size: 2rem;
  color: #333;
}

form {
  gap: 20px;
}

.v-btn {
  margin-top: 20px;
}

.error--text .v-text-field__input,
.error--text .v-select__selections {
  border: 1px solid red;
}

.position-absolute {
  position: absolute;
}
</style>
