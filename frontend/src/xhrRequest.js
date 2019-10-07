import { local } from "./constant";
import getAccessToken from "./Authorization";

export default function sendRequest(url, method, postObj) {
  let request;

  if (method === "post") {
    request = fetch(local.baseUrl + url, {
      method: method,
      body: JSON.stringify(postObj ? postObj : {}),
      headers: new Headers({
        Accept: "application/json",
        "Content-Type": "application/json",
        Authorization: "Bearer " + getAccessToken()
      })
    });
  } else if (method === "get" || method === "delete") {
    request = fetch(local.baseUrl + url, {
      method: method,
      headers: new Headers({
        Accept: "application/json",
        "Content-Type": "application/json",
        Authorization: "Bearer " + getAccessToken()
      })
    });
  }

  return request.then(response => response).catch(error => error);
}
