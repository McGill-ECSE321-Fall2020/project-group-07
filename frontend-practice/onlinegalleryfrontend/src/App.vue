<template> 
  <v-app>   
      <Registration>
      </Registration>
  </v-app>
</template>

<script>
import Registration from './components/RegistrationPage';

export default {
  name: 'App',

  components: {
    Registration,
  },
  data: () => ({

  }),
};
</script>
