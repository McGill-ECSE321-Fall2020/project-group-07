<template>
  <v-app>
   <Uploader username="dabinci"/>
  </v-app>
</template>

<script>
import Uploader from "@/components/Uploader";

export default {
  name:"app",
  components: {Uploader}
}

</script>

<!--<script>-->
<!--import axios from 'axios';-->

<!--export default {-->
<!--  name: 'app',-->
<!--  data(){-->
<!--    return {-->
<!--      uploadedFile: null,-->
<!--      reader: new FileReader(),-->
<!--      transmittedFile:null,-->
<!--      transmitWidth:600,-->
<!--      transmitHeight:null,-->
<!--    }-->
<!--  },-->
<!--  methods:{-->
<!--    onFileSelected(event){-->
<!--      this.uploadedFile=event.target.files[0];-->
<!--      this.reader.readAsDataURL(this.uploadedFile);-->
<!--      this.reader.onload = () =>{-->
<!--        this.transmit();-->
<!--      };-->
<!--    },-->
<!--    transmit(){-->
<!--      axios.put('http://[BUCKET NAME].s3.us-east-1.amazonaws.com/encodedImg.txt',this.reader.result)-->
<!--        .then(res =>{-->
<!--          console.log(res);-->
<!--        })-->
<!--    },-->
<!--    download(){-->
<!--      axios.get('http://[BUCKET NAME].s3.us-east-1.amazonaws.com/encodedImg.txt').then(res=>{-->
<!--        console.log(res);-->
<!--        this.downloadedEncoding=res.data;-->
<!--      })-->
<!--    }-->
<!--  }-->
<!--}-->
<!--</script>-->

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}


</style>
