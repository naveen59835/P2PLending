import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

 //apiBaseUrl = environment.apiBaseUrl + "/review-service";

url="https://lendinglane.stackroute.io/api/v1/review/add"
urlrating="https://lendinglane.stackroute.io/api/v1/review/getAverage"
  constructor(private httpclient:HttpClient) { }


addreview(id:any,data:any)
{

  return this.httpclient.post<any>(`${this.url}/${id}`,data)
}

getrating(id:any)
{
  return this.httpclient.get<any>(`${this.urlrating}/${id}`)
}


}
