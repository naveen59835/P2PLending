import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RecommendedBorrower } from '../model/RecommendedBorroer';

@Injectable({
  providedIn: 'root'
})
export class RecommendationService {

 //apiBaseUrl = environment.apiBaseUrl + "/recommendation-service";


  url:string="https://lendinglane.stackroute.io/api/v1/recommendation/get"
  constructor(private httpClient:HttpClient) { }
  getbBorrower(creditScore:any)
  {
    return this.httpClient.get<Array<RecommendedBorrower>>(`${this.url}/${creditScore}`)
  }


}
