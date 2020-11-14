<template>
    <div class="placeholder">
      <div>Admin login</div>
      <v-btn @click="login">
        login
      </v-btn>
    </div>
  </template>

<script>

export default {
  name: 'admin-login',
  components: {

  },
  data(){
    return{
      username:""
    }
  },
  methods:{
    login(){
      this.$router.push({name:"/admin-portal", params: {username:"dali"}});     // to be replaced by username from the artist login form
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
