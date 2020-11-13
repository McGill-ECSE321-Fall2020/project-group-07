<template>
    <div class="placeholder">
      <div> artist login</div>
      <v-btn @click="login">
        login
      </v-btn>
    </div>
  </template>

<script>

export default {
  name: 'artist-login',
  components: {

  },
  data(){
    return{
      username:""
    }
  },
  methods:{
    login(){
      this.$router.push({name:"/artistpage", params: {username:"dali"}});     // to be replaced by username from the artist login form
    }
  }

}
</script>

<style>

.placeholder{
  padding-top: 300px;
  text-align: center;
}

</style>
