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
    name: 'artist-login-form',
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
        axios.get(`https://onlinegallery-backend-g7.herokuapp.com/getArtistByUsername/${this.username}`)
        .then(res=>{
          // console.log(res);
          this.responseMsg="logged in!";
          this.$router.push({name:"/artist-portal", params: {username:this.username, profileId:res.data.profileId}});     // to be replaced by username from the artist login form
        })
        .catch(error=>{
          this.responseMsg=error.response.data;
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