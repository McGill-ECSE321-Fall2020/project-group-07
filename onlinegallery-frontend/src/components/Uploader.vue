<template>
    <v-container>
        <v-card flat>
            <v-file-input label="upload your image" outlined dense @change="uploadResize"/>
            <v-card-text>{{this.response}}</v-card-text>
        </v-card>
    </v-container>
</template>

<script>
// import axios from 'axios';

export default {
  name: 'uploader',
  data(){
    return {
      disabled:true,
      reader:new FileReader(),
      transmitWidth:600,
      transmitHeight:null,
      transmittedFile:null,
      filename:null,
      timeSubmitted:null,
      response:"",
    }
  },
  props:["username"],
  methods:{
    uploadResize(file){
        if (file===undefined){
          this.disabled=true;
          this.reader=new FileReader();
          this.transmitHeight=null;
          this.transmittedFile=null;
        }
        else{
          this.reader.onload=()=>{
            let img=new Image();
            img.src=this.reader.result;
            img.onload=()=>{
              this.transmitHeight=Math.round((this.transmitWidth/img.width)*img.height);
              let elem=document.createElement('canvas');
              elem.width=this.transmitWidth;
              elem.height=this.transmitHeight;

              let ctx=elem.getContext('2d');
              ctx.drawImage(img,0,0,this.transmitWidth,this.transmitHeight);
              this.transmittedFile=ctx.canvas.toDataURL(img);
              this.disabled=false;

              this.generateFilename();
              this.$emit('upload-ready',this.filename, this.transmittedFile);

            }
          }
          this.reader.readAsDataURL(file);
        }
    },

    generateFilename(){
        let d = new Date();
        this.timeSubmitted=d.toLocaleDateString()+"_"+d.toLocaleTimeString();
        this.timeSubmitted=this.timeSubmitted.replace(" ","")
        this.timeSubmitted=this.timeSubmitted.replace(":","")
        this.timeSubmitted=this.timeSubmitted.replace(":","")
        this.timeSubmitted=this.timeSubmitted.replace("/","")
        this.timeSubmitted=this.timeSubmitted.replace("/","")
        this.timeSubmitted=this.timeSubmitted.replace("/","")
        this.filename=this.username+"_"+this.timeSubmitted+"_"+this.transmitWidth+"_"+this.transmitHeight;
    }
  }
}
</script>

<style>
.file-input{
  width:35%;
  margin: auto;
}

</style>
