<template>


        <v-flex xs4 class="grey lighten-4">
            <v-container style="position: relative;top: 13%;" class="text-xs-center">

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
      v-model="password"
       :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
       :rules="passwordRules"
       :type="show1 ? 'text' : 'password'"
       name="input-10-1"
       label="Password"
       counter
       @click:append="show1 = !show1"
    ></v-text-field>


    <v-btn
      class="mr-4"
      @click="Login"
    >
      Login
    </v-btn>
            </v-form>
         </v-container>
     </v-flex>


    
</template>

<script>
export default {
    name: 'login',

    data: () => ({
      username: '',
      usernameRules: [
        v => !!v || 'Username is required',
      ],
      
      password: '',
      passwordRules: [
        v => !!v || 'Password is required',
      ],
      
    }),

    methods: {
      Login(){
      }
    }
    
}
</script>

<style scoped>

</style>