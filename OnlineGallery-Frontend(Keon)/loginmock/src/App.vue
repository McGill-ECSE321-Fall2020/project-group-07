  
<template> 
  <v-app>   
      <login>
      </login>
  </v-app>
</template>

<script>
import login from './components/login';
export default {
  name: 'App',
  components: {
    login,
  },
  data: () => ({
  }),
};
</script>

