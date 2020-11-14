  
<template> 
  <v-app>   
      <customerLoginForm>
      </customerLoginForm>
  </v-app>
</template>

<script>
import customerLoginForm from './components/customerLoginForm';
export default {
  name: 'App',
  components: {
    customerLoginForm,
  },
  data: () => ({
  }),
};
</script>

