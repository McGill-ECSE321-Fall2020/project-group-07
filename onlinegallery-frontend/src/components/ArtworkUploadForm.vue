<template>
  <v-container>
      <v-card height="750x">

          <v-form class="pa-4 pt-6">
            <v-text-field v-model="name" label="artwork title" required />
            <v-text-field v-model="description" label="description" required />
            <v-text-field v-model="medium" label="medium" required />
            <v-text-field v-model="price" label="price" required />
            <v-text-field v-model="dimension" label="dimension" required />
            <v-text-field v-model="weight" label="weight" required />
            <v-text-field v-model="commission" label="commission" required />
            <Uploader @upload-ready="handleUpload" username="jhansolo"/>
            <div class="text-center">
              <p>{{response}}</p>
            </div>

          </v-form>
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
      </v-card>

  </v-container>
</template>

<script>
  import Vue from 'vue'
  import axios from 'axios';
  import VueAxios from 'vue-axios';
  Vue.use(VueAxios,axios);

  import Uploader from "@/components/Uploader";

  export default {
    name: 'Registration',
    components:{Uploader},
    props:["username"],
    data:()=>{
      return{
        name:"",
        description:"",
        medium:"",
        price: null,
        status:1,
        dimension:"",
        weight: null,
        commission: null,
        numViews: 0,
        url:"",
        imgEncoding:"",
        response:"",
      }
    },
    methods: {
      handleUpload(url,imgEncoding){
        this.url=url;
        this.imgEncoding=imgEncoding;
      },
      upload(){
        let dto={
          // username:this.$props.username,
          username:"jhansolo",
          name:this.name,
          description:this.description,
          medium:this.medium,
          price: parseFloat(this.price),
          status:1,
          dimension:this.dimension,
          weight: parseFloat(this.weight),
          commission: parseFloat(this.commission),
          numViews: 0,
          url:this.url
        }

        this.transmit(dto);
      },
      transmit(dto){
        this.response="submitting ...";
        axios.put(`http://og-img-repo.s3.us-east-1.amazonaws.com/${this.url}`,this.imgEncoding)
          .then(()=>{
            this.response="done!"
          })
        .catch(()=>{
          this.response="something went wrong, try again later";
        })

        axios.post("https://onlinegallery-backend-g7.herokuapp.com/createArtwork",dto)
        .then((res)=>{
          console.log(res);
        })
        .catch((error)=>{
          console.log(error);
        })
      },

     resetForm(){
        this.name="";
        this.description="";
        this.medium="";
        this.price=null;
        this.status=1;
        this.dimension="";
        this.weight=null;
        this.commission=null;
        this.numViews=0;
        this.url="";
        this.imgEncoding="";
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