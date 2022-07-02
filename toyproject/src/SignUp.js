import { Button, Grid, Paper, TextField, Typography, Link } from '@mui/material';
import React from 'react';
import {signup} from './ApiService';


class SignUp extends React.Component{
    constructor(props){
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }
    handleSubmit(event){
        const data = new FormData(event.target);
        const username = data.get("username");
        const email = data.get("email");
        const password = data.get("password");
        signup({email: email , username:username, password:password}).then((response)=>{
            window.location.href="/login";
        })
    };

    render(){
        return(
            <>
            <Paper component="main" style={{marginTop: "8%"}}>
            <form noValidate onSubmit={this.handleSubmit}>
                <Grid item xs={12}>
                    <Typography component="h1" variant='h5'>
                        계정생성
                    </Typography>

                </Grid>
                <Grid item xs ={12}>
                    <TextField autoComplete='fname' name="username" variant='outlined'required fullWidth id="username" label="사용자 이름" autoFocus>
                    </TextField>

                </Grid>
                <Grid item xs ={12}>
                    <TextField   variant='outlined'required fullWidth id="email" label="이메일 주소" name='email' autoComplete="email">
                    </TextField>

                </Grid>
                <Grid item xs ={12}>
                    <TextField   variant='outlined'required fullWidth id="password" label="패스워드" type="password" name='password' autoComplete="current-password">
                    </TextField>

                </Grid>
                <Grid item xs={12}>
                    <Button type="submit" fullWidth variant='contained' color="primary">계정생성</Button>

                </Grid>
                <Grid container justify="flex-end">
                    <Grid item>
                        <Link href='/login' variant="body2">
                            이미 계정이 있습니까? 로그인 하세요 
                        </Link>
                    </Grid>
                </Grid>
            </form>

        </Paper>
            </>

        )
        
    }
}
export default SignUp;