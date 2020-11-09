<template>
  <v-container>
        <v-card @click="expand" @mouseover="hover = true" @mouseleave="hover=false;" :class="{active:hover}" class="m-0 ">
             <v-img v-bind:src="imgSrc"/>
        </v-card>
        <PopUp :dialog.sync="clicked" @closeDialog="clicked=false" v-bind:imgSrc="imgSrc" v-bind:title="title" v-bind:desc="desc" />
  </v-container>
</template>

<script>
import PopUp from "@/components/PopUp";
export default {
  name: 'image-tile',
  components:{PopUp},
  props: ["imgSrc","title","desc"],
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
}

.title{
  font-family: Roboto;
  text-align: center;
}
</style>