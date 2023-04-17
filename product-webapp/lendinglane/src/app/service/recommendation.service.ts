import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RecommendedBorrower } from '../model/RecommendedBorroer';

@Injectable({
  providedIn: 'root'
})
export class RecommendationService {
  url:string="http://localhost:9002/api/v1/recommendation/get"
  constructor(private httpClient:HttpClient) { }
  getbBorrower(creditScore:any)
  {
    return this.httpClient.get<Array<RecommendedBorrower>>(`${this.url}/${creditScore}`)
  }


}
