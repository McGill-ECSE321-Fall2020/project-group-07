<template>
  <v-container>
      <v-card class="mx-auto" style="max-width: 500px;">

          <v-form class="pa-4 pt-6">
            <v-text-field v-model="username" label="Username" required/>
            <v-text-field v-model="password" :type="'password'" name="input-10-1" label="Password" />

          </v-form>

          <v-card-actions>
            <v-btn @click="login" outlined>login</v-btn>
            <v-spacer/>
            <v-btn @click="resetForm" outlined>Clear</v-btn>
          </v-card-actions>

        <v-card-text class="text-center">{{responseMsg}}</v-card-text>
      </v-card>

  </v-container>
</template>

<script>
  import Vue from 'vue'
  import axios from 'axios';
  import VueAxios from 'vue-axios';
  Vue.use(VueAxios,axios);

  export default {
    name: 'admin-login-form',
    data:()=>{
      return{
        username:"",
        password:"",
        responseMsg:""
      }
    },
    methods: {
      login(){
        this.responseMsg="logging in ..."
        axios.get(`https://onlinegallery-backend-g7.herokuapp.com/getAdmin`)
        .then(res=>{
        
          if(this.username.localeCompare(res.data.username)){
            throw new Error("Incorrect Username!");
          } else if(this.password.localeCompare(res.data.password)){
            throw new Error("Incorrect Password!");
          } else{
            this.responseMsg="logged in!";
            this.$router.push({name:"/admin-portal", params: {username:this.username}});  
          } 
        })
        .catch(error=>{
          this.responseMsg=error;
        })
      },
      resetForm(){
        this.username="";
        this.password="";
        this.responseMsg="";
      }
    }
  }

</script>

<style>
.active{
  cursor: pointer;
}
.title{
  font-family: Roboto;
  text-align: center;
}
.card{
  border: 1px solid black !important;
}
</style>