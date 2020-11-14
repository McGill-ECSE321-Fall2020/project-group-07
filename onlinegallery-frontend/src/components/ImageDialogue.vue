<template>
  <v-container>
        <v-dialog :value="dialog" @click:outside="closeDialog()" overlay-color="white" overlay-opacity="0.9" width="1600">
          <div class="card-container">
            <v-card color="#ffffff" class="rounded-lg" width="1600" height="800">
               <v-row no-gutters >
                        <v-col cols="8">
                              <div height="800">
                                <perfect-scrollbar>
                              <v-img :src="imgUrl" width="1200" min-height="800"/>
                                </perfect-scrollbar>
                              </div>
                        </v-col>
                        <v-col cols="4">

                        <div class="title">
                           <v-card-title class="justify-center text-sm-h5 title" >
                              {{artname}}
                            </v-card-title>
                        </div>

                        <div class="subtitle">
                           <v-card-title class="justify-center text-sm-body-2 subtitle">
                             {{dimension}}, {{medium}}
                            </v-card-title>

                           <v-card-title class="justify-center text-sm-body-1 subtitle" >

                             <v-btn  outlined x-small>
                               {{ artistname }}
                             </v-btn>
                           </v-card-title>
                        </div>


                           <v-card-text class="text-sm-h5 pl-2 text-justify">
                               <div class="body">
                                      {{artdesc}}
                               </div>
                            </v-card-text>

                             <div class="text-center">
                               <v-btn depressed elevation="2" small outlined @click="addToCart">add to cart</v-btn>
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
  name: 'image-dialog',
  props: ["dialog","artid","imgUrl","artname","artistname","artdesc","medium", "dimension", "price"],
  methods:{
    closeDialog(){
      this.$emit('closeDialog');
    },
    addToCart(){
      this.$emit("added-to-cart", this.artid)
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
  padding-top: 20px;
}

.subtitle{
  padding-bottom: 0px !important;
}

.body{
  height:500px;
  overflow: scroll !important;
  word-break: normal;
  font-size:0.5em !important;
  overflow-x:hidden !important;
  overflow-y: hidden !important;
  padding-left: 60px;
  padding-right: 60px;
}
.ps{
  height:800px;
}

</style>