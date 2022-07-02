import logo from './logo.svg';
import './App.css';
import Todo from './Todo';
import AddTodo from './AddTodo'
import React from 'react';
import { API_BASE_URL } from "./app-config";
import { call, signout } from './ApiService';
import { Button } from '@mui/material';

const ACCESS_TOKEN = "ACCESS_TOKEN";

class App extends React.Component{
  
  constructor(props){
    super(props);
    this.state = {
      items: [
         
      ]
    }
  }

  componentDidMount(){
    call("/todo","GET",null).then((response)=>this.setState({items:response.data}))


    
  }

  add =(item) =>{
    call("/todo","POST",item).then((response)=>this.setState({itmes:response.data}))
  }

  delete = (item)=>{
    call("/todo","DELETE",item).then((response)=>this.setState({items:response.data}))
  }
  
  update =(item) =>{
    call("/todo","PUT",item).then((response)=>this.setState({items:response.data}))
  }
 
  
  
 

  render(){
    var todoItems = this.state.items.map((item,idx)=>(
      <Todo item={item} key={item.id} delete={this.delete} update={this.update}/>
    ))
    return(
      <>
      <Button color="inherit" onClick={signout}>로그아웃</Button>
      <AddTodo add={this.add}/>
       <div>{todoItems}</div>
      
      </>
     
    

    )
  }
}

export default App;
