<template>
  <v-container>
      <v-card class="mx-auto" style="max-width: 500px;">

          <v-form class="pa-4 pt-6">
            <v-text-field v-model="username" label="Username" required/>
            <v-text-field v-model="firstName" label="First Name" required />
            <v-text-field v-model="lastName" label="Last Name" required />
            <v-text-field v-model="email" label="E-mail" required />
            <v-text-field v-model="password" :type="'password'" name="input-10-1" label="Password" />

<!--            < checkbox component v-model="asCustomer" /> -->
          </v-form>

          <v-card-actions>
            <v-btn @click="register" color="primary">Register</v-btn>
            <v-spacer/>
            <v-btn text @click="resetForm">Clear</v-btn>
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
    name: 'Registration',
    data:()=>{
      return{
        username:"",
        firstName:"",
        lastName:"",
        email:"",
        password:"",
        responseMsg:"",
        asArtist: false,
        asCustomer: false
      }
    },
    methods: {
      register(){
        let signup ={
          username: this.username,
          firstName: this.firstName,
          lastName: this.lastName,
          email: this.email,
          password: this.password
        }
        console.log(signup)

        axios.post("https://onlinegallery-backend-g7.herokuapp.com/createRegistration",signup)
        .then(res=>{
          this.responseMsg=this.username+" registered!"
          console.log(res);
        })
        .catch(error=>{
          this.responseMsg=error.response.data;
        })

        // if asCustomer, axios.put("...../setAsCustomer/username).....
      },

      resetForm(){
        this.username="";
        this.firstName="";
        this.lastName="";
        this.email="";
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