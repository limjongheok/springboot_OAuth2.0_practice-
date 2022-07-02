import { AlternateEmail } from "@material-ui/icons";
import { API_BASE_URL } from "./app-config";


export function signin(userDTO){
  return call("/auth/signin", "POST", userDTO).then((response)=>{
    

    if(response.token){
      localStorage.setItem("ACCESS_TOKEN",response.token);
      window.location.href="/";
    }
  })
}



export function call(api, method, request) {
  let headers = new Headers({
    "Content-Type" : "application/json",
  });
  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  if(accessToken && accessToken !== null){
    headers.append("Authorization","Bearer"+accessToken);
  }
  let options={
    headers : headers,
    url : API_BASE_URL + api,
    method:method,
  }
  
  
  if (request) {
    // GET method가 아닐시 
    options.body = JSON.stringify(request);
  }
  return fetch(options.url, options).then((response) =>
    response.json().then((json) => {
      if (!response.ok) {
        // response.ok가 true이면 정상적인 리스폰스를 받은것, 아니면 에러 리스폰스를 받은것.
        return Promise.reject(json);
      }
      return json;
    })
  ).catch((error)=>{
    console.log(error.status);
    if(error.status === 403){
      window.location.href = "/login";
    }
    return Promise.reject(error)
  })
  ;
}
// reton json;
// request가 투르일시 null이 아닐시 즉 get 방식이 아닐시 fetch 로 json 반환 