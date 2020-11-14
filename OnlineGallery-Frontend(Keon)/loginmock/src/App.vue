  
<template> 
  <v-app>   
      <aboutPage>
      </aboutPage>
  </v-app>
</template>

<script>
import aboutPage from './components/aboutPage';
export default {
  name: 'App',
  components: {
    aboutPage,
  },
  data: () => ({
  }),
};
</script>

