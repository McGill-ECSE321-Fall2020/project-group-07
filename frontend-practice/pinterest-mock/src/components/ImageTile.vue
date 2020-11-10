<template>
  <v-container>
        <v-card @click="expand" @mouseover="hover = true" @mouseleave="hover=false;" :class="{active:hover}" class="m-0 card">
             <v-img v-bind:src="imgSrc"/>
        </v-card>
        <ImageDialogue :dialog.sync="clicked" @closeDialog="clicked=false" v-bind:imgSrc="imgSrc" v-bind:title="title" v-bind:desc="desc" />
  </v-container>
</template>

<script>
import ImageDialogue from "@/components/ImageDialogue";
export default {
  name: 'image-tile',
  components:{ImageDialogue},
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

.card{
  border: 1px solid black !important;
}
</style>