<template>
  <v-container>
            <Uploader @upload-ready="handleUpload" :username="username"/>

            <div class="text-center">
              <p>{{response}}</p>
            </div>

            <v-row>
              <v-spacer/>

              <v-col :cols="3">
                <v-btn @click="upload" outlined>submit</v-btn>
              </v-col>

              <v-col :cols="3">
                <v-btn  @click="resetForm" outlined>Clear</v-btn>
              </v-col>

              <v-spacer/>
            </v-row>

  </v-container>
</template>

<script>
  import Vue from 'vue'
  import axios from 'axios';
  import VueAxios from 'vue-axios';
  Vue.use(VueAxios,axios);

  import Uploader from "@/components/Uploader";

  export default {
    name: 'profile-pic-uploader',
    components:{Uploader},
    props:["username","desc"],
    data:()=>{
      return{
        url:"",
        response:"",
        imgEncoding:""
      }
    },
    methods: {
      handleUpload(url,imgEncoding){
        this.url=url;
        this.imgEncoding=imgEncoding;
      },
      upload(){
        let dto={
          username:this.$props.username,
          url:this.url,
          selfDescription:this.$props.desc
        }
        this.transmit(dto);
      },
      transmit(dto){
        console.log(dto);
        this.response="submitting ...";
        axios.put(`http://og-img-repo.s3.us-east-1.amazonaws.com/${this.url}`,this.imgEncoding)
          .then(()=>{
            this.response="done!"
          })
        .catch(()=>{
          this.response="something went wrong, try again later";
        })

        axios.put("https://onlinegallery-backend-g7.herokuapp.com/updateProfile",dto)
        .then((res)=>{
          console.log(res);
        })
        .catch((error)=>{
          console.log(error);
        })
      },

     resetForm(){
        this.url="";
        this.response="";
     }
    }
  }

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