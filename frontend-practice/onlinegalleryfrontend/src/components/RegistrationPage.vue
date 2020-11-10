<template>
<v-container>
    <v-card
    class="mx-auto"
    style="max-width: 500px;"
  >
      <v-toolbar>
      <v-card-title class="title font-weight-regular">
        Sign Up
      </v-card-title>
      </v-toolbar>
  <v-form
    ref="form"
    v-model="form"
    class="pa-4 pt-6"
  >    
   <v-text-field
      v-model="username"
      :counter="10"
      :rules="usernameRules"
      label="Username"
      required
    ></v-text-field>

    <v-text-field
      v-model="firstName"
      :rules="firstNameRules"
      label="First Name"
      required
    ></v-text-field>
   
    <v-text-field
      v-model="lastName"
      :rules="lastNameRules"
      label="Last Name"
      required
    ></v-text-field>

    <v-text-field
      v-model="email"
      :rules="emailRules"
      label="E-mail"
      required
    ></v-text-field>

    <v-text-field
      v-model="password"
       :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
       :rules="passwordRules"
       :type="show1 ? 'text' : 'password'"
       name="input-10-1"
       label="Password"
       counter
       @click:append="show1 = !show1"
    ></v-text-field>

    <v-select
      v-model="select"
      :items="items"
      :rules="[v => !!v || 'Selection is required']"
      label="Select your title"
      required
    ></v-select>
  </v-form>
<v-divider></v-divider>
    <v-card-actions>
      <v-btn
      :disabled="!form"
        :loading="isLoading"
        class="white--text"
        color="deep-purple accent-4"
        depressed
      >
        Register
    </v-btn> 
        <v-spacer></v-spacer>
      <v-btn
        text
        @click="$refs.form.reset()"
      >
        Clear
      </v-btn>  
    </v-card-actions>
    </v-card>
  <ImageDialogue :dialog.sync="clicked" @closeDialog="clicked=false" v-bind:imgSrc="imgSrc" v-bind:title="title" v-bind:desc="desc" />
</v-container>
</template>

<script>
  import ImageDialogue from "@/components/ImageDialogue";
  export default {
    name: 'Registration',
    components:{ImageDialogue},
    props: ["imgSrc","title","desc"],
    data: () => ({
      form:false,
      isLoading: false,
      username: '',
      usernameRules: [
        v => !!v || 'Username is required',
        v => (v && v.length <= 10) || 'Name must be at most 10 characters',
      ],
      firstName: '',
      firstNameRules: [
        v => !!v || 'First Name is required',
      ],
      lastName: '',
      lastNameRules: [
        v => !!v || 'Last Name is required',
      ],
      email: '',
      emailRules: [
        v => !!v || 'E-mail is required',
        v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
      ],
      show1: false,
      show2: true,
      show3: false,
      show4: false,
     
     password: '',
      passwordRules: [
        v => !!v || 'Password is required',
        v => (v && v.length >= 5)|| 'Password must be at least 5 characters',
      ],
      items: ['Customer','Artist', 'Admin',],
    }),
    methods: {
      register(){

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