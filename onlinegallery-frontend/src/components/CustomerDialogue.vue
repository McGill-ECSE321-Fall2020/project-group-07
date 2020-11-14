<template>
  <v-container>
        <v-dialog :value="dialog" @click:outside="closeDialog()" overlay-color="white" overlay-opacity="0.9" width="450">
            <v-card color="#ffffff">
                <div class="title">
                  <v-card-title class="justify-center text-sm-h5 title" >
                      Are you signing up as a Customer?
                  </v-card-title>
                </div>
                  <v-card-text class="text-sm-h5 pl-2 text-justify">
                      <div class="body">
                          Select your answer
                           <div class="text-xs-center">
                           <v-btn
                              depressed
                              elevation="2"
                              small
                              outlined
                              @click="yes"
                            >YES</v-btn>
                              <v-btn
                              depressed
                              elevation="2"
                              small
                              outlined
                              @click="no"
                              >NO</v-btn>
                        </div>
                      </div>
                   </v-card-text>
            </v-card>
        </v-dialog>
  </v-container>
</template>

<script>
import Vue from 'vue'
import axios from 'axios';
import VueAxios from 'vue-axios';
Vue.use(VueAxios,axios);

export default {
  name: 'customer-popup',
  props: ["dialog", "username"],
  methods:{
    closeDialog(){
      this.$emit('closeDialog');
    },
    yes(){
      axios.put(`https://onlinegallery-backend-g7.herokuapp.com/setCustomer/${this.$props.username}`)
      this.$emit('closeDialog');
    },
    no(){
      this.$emit('closeDialog');
    }
  },
  data(){
    return{
      ops:{}
    }
  }
};
</script>

<style scoped>
.title{
  height: 100px;
  padding-top: 25px;
}

.body{
  height:250px;
  text-align: center;
  font-size:0.5em !important;
  padding-top: 60px;

}
.text-xs-center{
 padding-top:15px;
}
</style>