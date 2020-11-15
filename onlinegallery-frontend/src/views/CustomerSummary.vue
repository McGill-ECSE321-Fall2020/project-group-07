<template>
   <v-card class = "fit" dark>
      <v-card-title>
         <v-text-field
         v-model="search"
         append-icon="mdi-magnify"
         label="Search"
         single-line
         hide-details
         ></v-text-field>
      </v-card-title>
      <v-data-table 
         :headers="headers"
         :items="info"
         :search="search"
         :loading="isLoading"
         loading-text="Loading... Please wait"
         no-data-text="No data to display"
      ></v-data-table>
   </v-card>
</template>

<script>
import Vue from 'vue';
import axios from 'axios';
import VueAxios from 'vue-axios';
Vue.use(VueAxios,axios);

export default {
   name: 'customer-summary',
   props:["username"],
   data () {
      return {
         search: '',
         headers: [
            {
            text: 'Date',
            align: 'start',
            value: 'date',
            },
            { text: 'Price', value: 'price' },
            { text: 'Commission', value: 'commission' },
            { text: 'Artist', value: 'artist' },
            { text: 'Artwork', value: 'artwork' },
            { text: 'Shipment', value: 'shipment'}
         ],
         info: [
            
         ],
         isLoading: true
      }
   },
   methods:{},
   mounted(){
      axios.get(`https://onlinegallery-backend-g7.herokuapp.com/generateSummary`)
      .then(res=>{
         let datas = res.data;
         for(let i = 0; i<datas.length; i++){

            let data = datas[i];

            if((data.customerName).localeCompare(this.username)) continue;

            let info ={
               price: data.price,
               commission: data.comission,
               date: data.datePurchased,
               artist: data.artistName,
               artwork: data.name,
               shipment: data.shipmentType
            }
            
            if(info!=undefined)this.info.push(info);
         }
         this.isLoading = false;
      }).catch(error=>{
         this.isLoading = false;
         console.log(error.reponse.data);
      })
   }
}
</script>

<style>

.fit{
   transform: scale(0.8);
   margin-top: 150px;
}

</style>
