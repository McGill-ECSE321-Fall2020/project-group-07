<template>
  <v-container>
    <v-card class="mx-auto" style="max-width: 500px">
      <v-toolbar>
        <v-card-title class="title font-weight-regular"> Login </v-card-title>
      </v-toolbar>

      <v-form class="pa-4 pt-6">
        <v-text-field v-model="username" label="Username" required />

        <v-text-field
          v-model="password"
          :type="'password'"
          name="input-10-1"
          label="Password"
        />
      </v-form>

      <v-divider></v-divider>
      <v-card-actions>
        <v-btn class="mr-4" @click="Login"> Login </v-btn>
      </v-card-actions>
      <v-card-text class="text-center">{{ responseMsg }}</v-card-text>
    </v-card>
  </v-container>
</template>


<script>
import Vue from "vue";
import axios from "axios";
import VueAxios from "vue-axios";
Vue.use(VueAxios, axios);
export default {
  name: "login",

  data: () => ({
    username: "",
    password: "",
    responseMsg: "",
  }),

  methods: {
    Login() {
      var loginInfo = {
        username: this.username,
        password: this.password,
      };
      console.log(loginInfo);
      axios
        .post(
          "https://onlinegallery-backend-g7.herokuapp.com/customerLogin", 
          loginInfo //not sure if its the right request
        ) 
        .then((res) => {
          this.responseMsg = this.username + " Logged in!";
          console.log(res);
        })
        .catch((error) => {
          this.responseMsg = error.response.data;
        });
    },
  },
};
</script>

<style scoped>
.active {
  cursor: pointer;
}
.title {
  font-family: Roboto;
  text-align: center;
}
.card {
  border: 1px solid black !important;
}
</style>