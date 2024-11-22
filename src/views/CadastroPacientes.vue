<template>
  <v-container fluid class="d-flex justify-center align-center" style="min-height: 100vh;">
    <v-row justify="center" align="center" class="w-100">
      <v-col cols="12" sm="8" md="6">
        <v-card class="pa-4">
          <h1 class="text-center mb-4">Cadastro de Pacientes</h1>
          <form @submit.prevent="validateAndSubmit">
            <v-text-field
              v-model="state.nome"
              :counter="255"
              label="Nome"
              required
              :class="{'error--text': v$.nome.$error}"
              @blur="v$.nome.$touch"
              @input="v$.nome.$touch"
            ></v-text-field>

            <v-text-field
              v-model="state.data_nascimento"
              label="Data de Nascimento"
              required
              type="date"
              :class="{'error--text': v$.data_nascimento.$error}"
              @blur="v$.data_nascimento.$touch"
              @input="v$.data_nascimento.$touch"
            ></v-text-field>

            <v-text-field
              v-model="state.telefone"
              label="Telefone"
              required
              type="tel"
              :class="{'error--text': v$.telefone.$error}"
              @blur="v$.telefone.$touch"
              @input="v$.telefone.$touch"
              mask="(###) ###-####"
            ></v-text-field>

            <v-textarea
              v-model="state.endereco"
              label="Endereço"
              required
              :class="{'error--text': v$.endereco.$error}"
              @blur="v$.endereco.$touch"
              @input="v$.endereco.$touch"
            ></v-textarea>

            <v-text-field
              v-model="state.email"
              label="E-mail"
              required
              :class="{'error--text': v$.email.$error}"
              @blur="v$.email.$touch"
              @input="v$.email.$touch"
            ></v-text-field>

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
import { required, email } from '@vuelidate/validators';
import axios from 'axios';

const statusOptions = ['Ativo', 'Inativo'];

const initialState = {
  nome: '',
  data_nascimento: '',
  telefone: '',
  endereco: '',
  email: '',
  status: null,
};

const state = reactive({ ...initialState });
const formInvalid = ref(false);
const formSubmitted = ref(false);

const rules = {
  nome: { required },
  data_nascimento: { required },
  telefone: { required },
  endereco: { required },
  email: { required, email },
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
        nome: state.nome,
        dataNascimento: state.data_nascimento,
        telefone: state.telefone,
        endereco: state.endereco,
        email: state.email,
        status: state.status === 'Ativo',
      };
      
      console.log('Payload:', payload);
      
      const response = await axios.post('http://localhost:8080/pacientes', payload);
      console.log('Paciente cadastrado com sucesso:', response.data);

      formSubmitted.value = true;
      clear();

      setTimeout(() => {
        formSubmitted.value = false;
      }, 5000);
    } catch (error) {
      console.error('Erro ao cadastrar o paciente:', error.response || error.message);
      alert('Ocorreu um erro ao cadastrar o paciente. Verifique os dados e tente novamente.');
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
.error--text .v-select__selections,
.error--text .v-textarea__input {
  border: 1px solid red;
}

.position-absolute {
  position: absolute;
}
</style>
