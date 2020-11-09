<template>
  <v-container>
        <v-dialog :value="dialog" @click:outside="closeDialog()" overlay-color="#ffffff" overlay-opacity="0.9" width="700">
          <div class="card-container">
            <v-card color="#ffffff" class="rounded-lg" width="700" max-height="350">
               <v-row no-gutters >
                        <v-col cols="8">
                              <v-img v-bind:src="imgSrc" width="500" height="350"/>
                        </v-col>
                        <v-col cols="4">

                        <div height="100" class="title">
                           <v-card-title class="justify-center text-sm-subtitle-1" >
                              {{title}}
                            </v-card-title>
                        </div>

                           <v-card-text class="body-2 pl-2 text-justify">
                               <div class="body">
                                  <perfect-scrollbar>
                                      {{desc}}
                                  </perfect-scrollbar>
                               </div>
                            </v-card-text>

                             <div class="text-center">
                               <v-btn
                                depressed
                                elevation="2"
                                x-small
                              >buy</v-btn>
                            </div>


                        </v-col>

                      </v-row>
            </v-card>
          </div>
        </v-dialog>

  </v-container>
</template>

<script>
import Vue from 'vue';
import PerfectScrollbar from 'vue2-perfect-scrollbar'
import 'vue2-perfect-scrollbar/dist/vue2-perfect-scrollbar.css'
Vue.use(PerfectScrollbar);
export default {
  name: 'popup',
  props: ["imgSrc","title","desc","dialog"],
  methods:{
    closeDialog(){
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
}

.body{
  height:200px;
  overflow: scroll !important;
  word-break: normal;
  font-size:0.5em !important;
  overflow-x:hidden !important;
  overflow-y: hidden !important;
  padding-left: 20px;
  padding-right: 20px;
}

.ps{
  height:200px;
}

</style>