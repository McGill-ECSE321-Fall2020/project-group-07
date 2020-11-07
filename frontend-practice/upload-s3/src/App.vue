<template>
  <div>
    <div class="hello">
      <input type="file" @change="onFileSelected"/>
      <button @click="download">download</button>
    </div>

    <div>
      <img v-bind:src="downloadedEncoding">
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'app',
  data(){
    return {
      uploadedFile: null,
      downloadedEncoding:null,
      reader: new FileReader()
    }
  },
  methods:{
    onFileSelected(event){
      this.uploadedFile=event.target.files[0];
      this.reader.readAsDataURL(this.uploadedFile);
      this.reader.onload = () =>{
        this.transmit();
      };
    },
    transmit(){
      axios.put('http://[BUCKET NAME].s3.us-east-1.amazonaws.com/encodedImg.txt',this.reader.result)
        .then(res =>{
          console.log(res);
        })
    },
    download(){
      axios.get('http://[BUCKET NAME].s3.us-east-1.amazonaws.com/encodedImg.txt').then(res=>{
        console.log(res);
        this.downloadedEncoding=res.data;
      })
    }
  }
}
</script>

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
