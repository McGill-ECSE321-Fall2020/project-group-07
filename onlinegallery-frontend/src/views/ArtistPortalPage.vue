<template>
    <v-container>
      <div class="artist-dash">
        <v-row>
          <v-col :cols="3">
            <v-card height="320">
              <v-img height="320" :src="imgEncoding" />
            </v-card>
          </v-col>

          <v-col :cols="9">
            <v-card height="320">
                <div>
                  <v-card-title> {{firstname}} {{lastname}} </v-card-title>
                </div>
                <div class="desc">
                  <perfect-scrollbar>
                    <v-card-text class="text-justify pa-5"> {{desc}} </v-card-text>
                  </perfect-scrollbar>
                </div>
            </v-card>
          </v-col>
        </v-row>

         <v-row>
              <v-col :cols="3">
                <v-btn small text @click="uploadProfile=true" disabled>change profile pic</v-btn>
              </v-col>

              <v-col :cols="2">
                <v-btn small text  @click="startEditBio">update bio</v-btn>
              </v-col>

              <v-col :cols="3">
                <v-btn small text  @click="startUpload" disabled>upload artworks</v-btn>
              </v-col>

              <v-col :cols="2">
                <v-btn small text @click="gotoMyPage">my page</v-btn>
              </v-col>

              <v-col :cols="2">
                <v-btn small text>view sales</v-btn>
              </v-col>
          </v-row>


        <v-dialog :value="dialog" width="600" @click:outside="dialog=false">

          <v-card height="610" width="600" class="pa-5">
            <div>
                <v-textarea v-model="desc" no-resize outlined clearable clear-icon="mdi-close-circle"  :value="desc" height="500" class="text-justify"/>
            </div>

            <div class="text-right">
              <v-btn outlined @click="updateProfile">
                done
              </v-btn>
            </div>
          </v-card>

        </v-dialog>

        <v-dialog :value="upload" width="600" @click:outside="upload=false">
          <v-card height="800" width="600" class="pa-5">
              <ArtworkUploadForm :username="this.username"/>
          </v-card>
        </v-dialog>

         <v-dialog :value="uploadProfile" width="600" @click:outside="uploadProfile=false">
          <v-card height="300" width="600" class="pa-5">
              <ProfilePicUploadForm :username="this.username" :desc="this.desc" @new-pic-ready="swapProfilePic"/>
          </v-card>
        </v-dialog>


      </div>

    </v-container>
</template>

<script>
import Vue from 'vue';
import PerfectScrollbar from 'vue2-perfect-scrollbar'
import 'vue2-perfect-scrollbar/dist/vue2-perfect-scrollbar.css'
import axios from 'axios';
import VueAxios from 'vue-axios';
Vue.use(PerfectScrollbar);
Vue.use(VueAxios,axios);

import ArtworkUploadForm from "@/components/ArtworkUploadForm";
import ProfilePicUploadForm from "@/components/ProfilePicUploadForm";

export default {
  name: 'artist-portal',
  components: {ArtworkUploadForm, ProfilePicUploadForm},
  props:["username","profileId"],
  data(){
    return{
      dialog:false,
      upload:false,
      uploadProfile:false,
      firstname:"",
      lastname:"",
      desc:"",
      imgUrl:"",
      imgEncoding:"",
      artistId:null,
      profileEncoding:null,
    }
  },

  methods:{
    swapProfilePic(newUrl,newEncoding){
      this.imgUrl=`http://og-img-repo.s3.us-east-1.amazonaws.com/${newUrl}`;
      this.imgEncoding=newEncoding;
    },
    startEditBio(){
      this.dialog=true;
    },
    updateProfile(){
      this.dialog=false;
      let profileDto={
        "username":this.username,
        "selfDescription":this.desc,
        "url":this.imgUrl
      }
      axios.put("https://onlinegallery-backend-g7.herokuapp.com/updateProfile",profileDto)
      .then(res=>{console.log(res)});
    },
    startUpload(){
      this.upload=true;
    },
    gotoMyPage(){
      this.$router.push({name:"/artist-portfolio", params: {artistid:this.artistId}});
    }
  },
  mounted(){
    axios.get(`https://onlinegallery-backend-g7.herokuapp.com/getArtistByUsername/${this.username}`)
    .then(res=>{
      axios.get(res.data.url).then(res=>{this.imgEncoding=res.data})
      this.firstname=res.data.firstname;
      this.lastname=res.data.lastname;
      this.desc=res.data.selfDescription;
      this.artistId=res.data.artistId;
      this.imgUrl=res.data.url;
    })
  }

}
</script>

<style>

.artist-dash{
  width: 75%;
  text-align: center;
  margin:auto;
  margin-top: 200px;
  /*background-color: blue;*/
}

.title{
  height:50px;
}

.ps{
  height:220px;
}

.desc{
  height:200px;
  overflow-y: hidden;
}

</style>
