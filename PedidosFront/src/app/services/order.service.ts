import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private url = 'http://localhost:8080/orders';

  constructor(private http: HttpClient) { }

  orderActual: any = {
    items: []
  };

  setOrder(order: any) {
    this.orderActual = order;
  }

  getOrder() {
    return this.orderActual;
  }

  confirmarOrden(order: any) {
    return this.http.post(`${this.url}/create`, order);
  }

}
