import React from "react";
import "./index.css";
import App from "./App";
import Login from "./Login";
import {BrowserRouter, Routes, Route, Link } from "react-router-dom";
import { Box, Typography } from "@mui/material";



function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {"Copyright © "}
      fsoftwareengineer, {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

class AppRouter extends React.Component {
  render() {
    return (
      <div>
        <BrowserRouter>
          <div>
            <Routes>
              <Route path="/login" element={<Login/>}>
                
              </Route>
              <Route path="/" element={<App/>}>
                
              </Route>
            </Routes>
          </div>
          <Box mt={5}>
            <Copyright />
          </Box>
        </BrowserRouter>
      </div>
    );
  }
}

export default AppRouter;