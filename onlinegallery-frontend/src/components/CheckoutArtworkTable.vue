<template>
  <v-container>
    <v-card width="800" height="800" flat>
      <div class="title">
        <v-card-title>purchase items</v-card-title>
      </div>

      <div class="table">
        <v-data-table :headers="headers" :items="artworks" height="220" hide-default-footer/>
      </div>

      <div class="subtotal">
        <v-card-subtitle class="text-right text-sm-body-1">subtotal:   ${{subtotal}}</v-card-subtitle>
      </div>

      <div class="title">
        <v-card-title>shipping options</v-card-title>
      </div>

      <div class="form">
         <v-form class="pl-3 pr-3">

           <v-radio-group v-model="deliveryMethod" row>
              <v-radio label="external delivery" value="0" @click="handleShipOption"/>
              <v-radio label="gallery pickup" value="1" @click="handleShipOption"/>
            </v-radio-group>

            <v-text-field v-model="destAddress" label="destination address" required solo outlined/>
            <v-text-field v-model="receiptName" label="recipient Name" required solo outlined/>
         </v-form>
      </div>

      <div class="subtotal">
        <v-card-subtitle class="text-right text-sm-body-1">shipping:   ${{shippingCost.toFixed(2)}}</v-card-subtitle>
      </div>

      <div class="total text-decoration-underline">
        <v-card-subtitle class="text-right text-sm-body-1">total: ${{totalCost.toFixed(2)}}</v-card-subtitle>
      </div>


      <div class="btn text-right pr-5">
        <v-btn  small outlined @click="loginPressed" :disabled="(destAddress.length==0 || receiptName.length==0)">next</v-btn>
      </div>

    </v-card>

    <v-dialog v-model="startLogin" overlay-color="white" class="rounded-lg" width="400">
      <v-card width="400" height="280" flat>
        <CustomerLoginForm @customer-loggedin="handleLoggedIn" @customer-not-loggedin="handleNotLoggedIn"/>
      </v-card>
    </v-dialog>

    <v-dialog v-model="loggedIn" overlay-color="white" class="rounded-lg" width="400">
      <v-card width="400" height="500" flat>

          <div class="title">
            <v-card-title>credit card info</v-card-title>
          </div>
           <v-form class="pa-4 pt-6" v-model="loggedIn">

            <v-text-field v-model="ccNum" label="credit card number" required solo outlined/>
            <v-text-field v-model="ccExp" label="credit card expiration date" required solo outlined/>
            <v-text-field v-model="ccCsv" label="credit card CSV code" required solo outlined/>
         </v-form>

      <div class="total text-decoration-underline">
        <v-card-subtitle class="text-right text-sm-body-1">total: ${{totalCost.toFixed(2)}}</v-card-subtitle>
      </div>

      <div class="btn text-right pr-5">
<!--        <v-btn  small outlined @click="handlePay" :disabled="(ccNum.length==0 || ccCsv.length==0 || ccExp.length==0 )">pay</v-btn>-->
            <v-btn  small outlined @click="this.handlePay">pay</v-btn>

      </div>

      </v-card>
    </v-dialog>


  </v-container>
</template>

<script>
import Vue from 'vue';
import axios from 'axios';
import VueAxios from 'vue-axios';
Vue.use(VueAxios,axios);
import CustomerLoginForm from "@/components/CustomerLoginForm";

export default {
  name:"checkout-artwork-table",
  components:{CustomerLoginForm},
  props:["ids"],
  data(){
    return{
      headers:[{
                    text:"Artwork Name",
                    align:'start',
                    sortable:false,
                    value:'artname'
                  },
                    {text:'Artist', value:'artistName', sortable: false},
                    {text:'Dimensions', value:'dimension', align: "center", sortable: false},
                    {text:'Weight (lb)', value:'weight', align: "center", sortable: false},
                    {text:'Price ($)', value:'price', align: "end"}
              ],
      artworks:[],
      subtotal:0,
      shippingCost:0,
      totalCost:0,
      startLogin:false,
      loggedIn:false,
      destAddress:"",
      srcAddress:"115 Normand, Montreal, QC",
      receiptName:"",
      customerName:"",
      customerId:"",
      deliveryMethod:null,
      ccNum:"",
      ccCsv:"",
      ccExp:"",
      paid:false,
      purchaseId:[],
      shipmentId:null
    }
  },
  mounted(){
    for (let i=0;i<this.$props.ids.length;i++){
      let nowId=this.$props.ids[i];
      axios.get(`https://onlinegallery-backend-g7.herokuapp.com/getAvailableArtworkDetail/${nowId}`)
      .then(res=>{
        this.subtotal+=res.data.price;
        this.shippingCost+=(parseFloat(res.data.weight)*110.34);
        this.totalCost=this.shippingCost+this.subtotal;
        let nowArt={
          artname:res.data.name,
          dimension:res.data.dimension,
          weight:res.data.weight,
          artistName:res.data.username,
          price:res.data.price
        }
        this.artworks.push(nowArt);
      })
      .catch(error=>{console.log(error)});
    }
  },
  methods:{
    loginPressed(){
      this.startLogin=true;
    },
    handleLoggedIn(username,customerId){
      this.customerName=username;
      this.customerId=customerId;

      console.log("hey im gathering purchase Ids");

      let promise=[]

      for (let i=0;i<this.$props.ids.length;i++){
        let purchaseDto={
              username:this.customerName,
              artworkId: this.$props.ids[i],
              shipmentType: parseInt(this.deliveryMethod)
          };

        promise.push(
            axios.post("https://onlinegallery-backend-g7.herokuapp.com/createPurchase",purchaseDto)
            .then(res=>{this.purchaseId.push(res.data.purchaseId)})
            .catch(error=>{console.log(error)}))
        }

        Promise.all(promise).then(()=>this.loggedIn=true);

    },
    handleNotLoggedIn(){
      this.loggedIn=false


    },
    handleShipOption(){
      if (this.deliveryMethod==="1"){
        this.destAddress=this.srcAddress;
        this.receiptName="GalleryAdmin"
      }
      else{
        this.destAddress="";
        this.receiptName="";
      }
    },
    handlePay(){
      this.loggedIn=false;
      this.startLogin=false;


      let shipmentDto={
        shipmentId:-1,
        sourceAddress:this.srcAddress,
        destinationAddress:this.destAddress,
        recipientName:this.receiptName,
        shippingCost:this.shippingCost,
        totalCost:this.totalCost,
        customerId:this.customerId,
        shipmentStatus:0,
        purchases:this.purchaseId
      }
      axios.post("https://onlinegallery-backend-g7.herokuapp.com/createShipment", shipmentDto)
      .then(res=>{console.log(res)})
      .then(err=>{console.log(err)})
    }

  }
}

</script>

<style>
.title{
  height:50px;
}

.table{
  height:220px;
}

.btn{
  margin-top: 20px;
  height:50px;
}

</style>