<template>
  <v-app>
    <v-container fluid>
      <v-row>
        <v-col cols="3">
          <v-navigation-drawer
            expand-on-hover
            rail
            app
            width="300"
            color="primary"
            dark
          >
            <v-list>
              <v-list-item
                prepend-avatar="data:image/png;base64,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"
                subtitle="Adm@gmail.com"
                title="Adm Lode "
              ></v-list-item>
            </v-list>

            <v-divider></v-divider>

            <v-list nav>
              <v-list-item
                prepend-icon="mdi-folder"
                title="Cadastro de Médicos"
                to="/medicos"
              ></v-list-item>
              <v-list-item
                prepend-icon="mdi-calendar"
                title="Cadastro de Agendas"
                to="/agendas"
              ></v-list-item>
              <v-list-item
                prepend-icon="mdi-account"
                title="Cadastro de Pacientes"
                to="/pacientes"
              ></v-list-item>
              <v-list-item
                prepend-icon="mdi-stethoscope"
                title="Cadastro de Consultas"
                to="/consultas"
              ></v-list-item>
            </v-list>
          </v-navigation-drawer>
        </v-col>

        <v-col>
          <v-main>
            <router-view />
          </v-main>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

<script>
export default {
  name: "AppLayout",
};
</script>

<style>
.v-navigation-drawer {
  height: 100vh;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
}

.v-list-item {
  margin-bottom: 8px;
  font-size: 1.1rem;
}
</style>
