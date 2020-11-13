<template>
  <v-container>
      <v-card class="mx-auto" style="max-width: 500px;">

          <v-form class="pa-4 pt-6">
            <v-text-field v-model="username" label="Username" required/>
            <v-text-field v-model="firstName" label="First Name" required />
            <v-text-field v-model="lastName" label="Last Name" required />
            <v-text-field v-model="email" label="E-mail" required />
            <v-text-field v-model="password" 
            name="input-10-1" 
            label="Password" 
            :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
            :type="show1 ? 'text' : 'password'"
            @click:append="show1 = !show1"
            />         
             </v-form>
         
          <v-card-actions>
            <v-btn @click="register" color="primary">Register</v-btn>
            <v-spacer/>
            <v-btn text @click="resetForm">Clear</v-btn>
          </v-card-actions>

        <v-card-text class="text-center">{{responseMsg}}</v-card-text>
      </v-card>
      <CustomerDialogue :dialog.sync="customerclicked" @closeDialog="customerclicked=false"
                       :username="username"
                      />
      <ArtistDialogue :dialog.sync="artistclicked" @closeDialog="artistclicked=false"
                       :username="username"
                      />

  </v-container>
</template>

<script>
  import Vue from 'vue'
  import axios from 'axios';
  import VueAxios from 'vue-axios';
  import ArtistDialogue from "@/components/ArtistDialogue";
  import CustomerDialogue from "@/components/CustomerDialogue";
  Vue.use(VueAxios,axios);

  export default {
    components:{CustomerDialogue, ArtistDialogue},
    name: 'Registration',
    data:()=>{
      return{
        artistclicked:false,
        customerclicked:false,
        show1 :false,
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
          password: this.password,
        }
        console.log(signup)
        axios.post("https://onlinegallery-backend-g7.herokuapp.com/createRegistration",signup)
        .then(res=>{
          this.customerclicked=true,
          this.artistclicked=true,
          this.responseMsg=this.username+" registered!"
          console.log(res);
        })
        .catch(error=>{
          this.responseMsg=error.response.data;
        })
      },

      resetForm(){
        this.show1 = "";
        this.username="";
        this.firstName="";
        this.lastName="";
        this.email="";
        this.password="";
        this.responseMsg="";
        this.asCustomer="";
        this.asArtist="";
      }
    }
  }


</script>

<style>
.active{
  cursor: pointer;
}
.checkbox{
  font-family: Roboto;
  text-align: center;
}
.card{
  border: 1px solid black !important;
}
</style>