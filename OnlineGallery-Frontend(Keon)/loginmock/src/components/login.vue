<template>
<v-container>
    <v-card
    class="mx-auto"
    style="max-width: 500px;"
  >
      <v-toolbar>
      <v-card-title class="title font-weight-regular">
        Login
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
 </v-form>

    <v-divider></v-divider>
    <v-card-actions>
    <v-btn
      class="mr-4"
      @click="Login"
    >
      Login
    </v-btn>
      </v-card-actions>
    </v-card>
</v-container>
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
        var loginInfo={
          username: this.username,
          password: this.password,
         
        }
        console.log(loginInfo)
      }
    }
    
}
</script>

<style scoped>

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