<template>
  <v-form
    ref="form"
    v-model="valid"
    lazy-validation
  >
    <v-text-field
      v-model="username"
      :counter="10"
      :rules="username"
      label="Username"
      required
    ></v-text-field>
    <v-text-field
      v-model="firstName"
      :counter="10"
      :rules="firstNameRules"
      label="First Name"
      required
    ></v-text-field>
   
    <v-text-field
      v-model="lastName"
      :counter="10"
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

    <v-btn
      class="mr-4"
      @click="register"
    >
      Register
    </v-btn>
  </v-form>
</template>

<script>
    export default {
      name: 'Registration',
    data: () => ({
      username: '',
      usernameRules: [
        v => !!v || 'Username is required',
        v => (v && v.length <= 10) || 'Name must be less than 10 characters',
      ],
      firstName: '',
      firstNameRules: [
        v => !!v || 'First Name is required',
        v => (v && v.length <= 10) || 'Name must be less than 10 characters',
      ],
      lastName: '',
      lastNameRules: [
        v => !!v || 'Last Name is required',
        v => (v && v.length <= 10) || 'Name must be less than 10 characters',
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
        v => /.+@.+\..+/.test(v) || 'Password must be valid',
      ],
      items: ['Customer','Artist', 'Admin',],
    }),
    methods: {
      register(){

      }
    }
  }
</script>

<style scoped>

</style>