import { createRouter, createWebHistory } from 'vue-router';

// Importação das views
import CadastroMedicos from '../views/CadastroMedicos.vue';
import CadastroAgendas from '../views/CadastroAgendas.vue';
import CadastroPacientes from '../views/CadastroPacientes.vue';
import CadastroConsultas from '../views/CadastroConsultas.vue';

// Definição das rotas
const routes = [
  { 
    path: '/medicos', 
    name: 'CadastroMedicos', 
    component: CadastroMedicos 
  },
  { 
    path: '/agendas', 
    name: 'CadastroAgendas', 
    component: CadastroAgendas 
  },
  { 
    path: '/pacientes', 
    name: 'CadastroPacientes', 
    component: CadastroPacientes 
  },
  { 
    path: '/consultas', 
    name: 'CadastroConsultas', 
    component: CadastroConsultas 
  },
  { 
    path: '/:pathMatch(.*)*', 
    redirect: '/medicos' 
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
