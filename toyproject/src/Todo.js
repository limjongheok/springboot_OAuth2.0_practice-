import { DeleteOutline, ThreeSixty } from "@material-ui/icons";
import { Checkbox, InputBase, ListItem, ListItemSecondaryAction, ListItemText ,IconButton,Button} from "@mui/material";
import React from "react";

class Todo extends React.Component{
    constructor(props){
        super(props);
        this.state = {item: props.item, readOnly: true};
        this.delete = props.delete;
        this.update = props.update;

    }
    deleteEventHandler = () =>{
        this.delete(this.state.item)
    }
    offReadOnlyMode= () =>{
        console.log("event", this.state.readOnly)
        this.setState({readOnly:false}, ()=>{
            console.log("readonly", this.state.readOnly);
        })
    }
    enterKeyEventHandler = (e) =>{
        if(e.key ==="Enter"){
            this.setState({readOnly: true})
            this.update(this.state.item);
        }
    }
    editEventHandler =(e) =>{
        const thisItem = this.state.item;
        thisItem.title = e.target.value;
        this.setState({item: thisItem});
    }

    chechboxEventHandler = (e) =>{
        const thisItem = this.state.item;
        thisItem.done = !thisItem.done;
        this.setState({item: thisItem});
        this.update(this.state.item);
    }

    render(){
        const item = this.state.item;
        return(
            <ListItem>
                <Checkbox checked={item.done} onChange={this.chechboxEventHandler}/>
                <ListItemText>
                    <InputBase inputProps={{"aria-label":"naked", readOnly: this.state.readOnly}} onClick={this.offReadOnlyMode} onChange={this.editEventHandler} onKeyPress={this.enterKeyEventHandler} type ="text" id={item.id} name={item.id} value={item.title} multiline={true} fullWidth={true}/>
                </ListItemText>
                <ListItemSecondaryAction>
                  <Button onClick={this.deleteEventHandler}>X</Button>
                </ListItemSecondaryAction>
            </ListItem>

            
        )
    }
}


export default Todo;