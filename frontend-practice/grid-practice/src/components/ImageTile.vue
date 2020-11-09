<template>
  <div data-app>
  <v-container>
    <v-row dense>
      <v-spacer/>
      <v-col cols="12" md="4">
        <v-card @click="expand" @mouseover="hover = true" @mouseleave="hover=false;" :class="{active:hover}">
             <v-img v-bind:src="imgSrc" id="thumb"/>
        </v-card>

            <v-dialog v-model="clicked" @mousemove="clicked=false" overlay-color="#ffffff" overlay-opacity="0.98" width="75%">

                    <v-card color="#ffffff" class="pa-2 v-virtual-scroll">
                      <v-row no-gutters >
                        <v-col cols="8">
                              <v-img v-bind:src="imgSrc" max-height="800"/>
                        </v-col>
                        <v-col cols="0.5" />
                        <v-col cols="3">
                        <v-list-item three-line>
                            <v-list-item-content>
                                      <v-card-title class="justify-center headline mb-1" >
                                        Art Title
                                      </v-card-title>

                                      <v-card-subtitle/>

                                      <v-card-subtitle class="text-justify overflow-auto">
                                        Pellentesque habitant mort per conubia nostra, per inceptos himenaeos. Quisque sed tincidunt nunc. Etiam consequat, lectus vel fermentum varius, magna tellus sollicitudin lectus, sit amet egestas ipsum nibh maximus ligula. Interdum et malesuada fames ac ante ipsum primis in faucibus. Integer luctus, dui in maximus congue, sem risus suscipit velit, id vehicula metus nisl at turpis. Vivamus laoreet nibh eu nulla dignissim, sit amet dictum risus vulputate. Nullam ante libero, mattis ac suscipit a, tempor quis diam. In hac habitasse platea dictumst. Donec laoreet consequat rutrum. Nam stique senectus et netus et malesuada fames ac turpis egestas. Class aptent taciti sociosqu ad litora torquent pharetra, velit nec viverra consequat, puru
                                      </v-card-subtitle>

                                     <div class="text-center">
                                       <v-btn
                                                      depressed
                                                      elevation="2"

                                                      small
                                                    >buy</v-btn>
                                    </div>
                            </v-list-item-content>
                        </v-list-item>

                        </v-col>
                        <v-col cols="0.5"></v-col>

                      </v-row>

                    </v-card>

          </v-dialog>
      </v-col>
      <v-spacer/>
    </v-row>
  </v-container>

  </div>

</template>

<script>

export default {
  name: 'image-frame',
  props: ["imgSrc"],
  data(){
    return{
      hover:false,
      clicked:false,
      thumbHeight:null
    }
  },
  methods:{
    expand(){
      this.clicked=true;
      console.log("clicked")

    }
  }
};
</script>

<style>
.active{
  cursor: pointer;
  /*filter:brightness(70%);*/
}

.title{
  font-family: Roboto;
  text-align: center;
}
</style>
