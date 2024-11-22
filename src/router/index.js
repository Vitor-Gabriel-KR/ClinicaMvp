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
  // Rota padrão (caso o usuário acesse uma URL inválida)
  { 
    path: '/:pathMatch(.*)*', 
    redirect: '/medicos' 
  }
];

// Criação do roteador
const router = createRouter({
  history: createWebHistory(), // Utiliza o modo de histórico HTML5
  routes,
});

export default router;
