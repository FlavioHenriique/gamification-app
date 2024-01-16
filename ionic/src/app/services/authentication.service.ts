import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Plugins } from '@capacitor/core';
import { Storage } from '@ionic/storage-angular';
import { BehaviorSubject, from, map, Observable, throwError} from 'rxjs';
import { environment } from 'src/environments/environment';
import { Usuario } from '../entity/Usuario';
const TOKEN_KEY = 'token';



@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  isAuthenticated: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  USUARIO_LOGADO: BehaviorSubject<Usuario> = new BehaviorSubject<Usuario>(null);
  token= '';
  
  constructor(private httpClient: HttpClient, private storage: Storage) {
    this.loadToken();
   }


   async loadToken(){
      //const token = await Storage.get({key: TOKEN_KEY});
      await this.storage.create();
      const token = await this.storage.get(TOKEN_KEY);
      if (token && token.value){
        this.token = token.value;
        this.isAuthenticated.next(true);
      }else{
        this.isAuthenticated.next(false);
      }
   }

  
   login(credentials: {email, password}): Observable<any>{
    var url = environment.URL_BASE + "/usuario/login?email=" 
       + credentials.email + "&senha=" + credentials.password;
    return this.httpClient.get<Usuario>(url).pipe(
      map(async(res) =>{
        this.USUARIO_LOGADO.next(res);
      },
      async(res) =>{      
        this.isAuthenticated.next(false);
        throw new Error("erro login");              
      }
    ));          
   }

   logout(): Promise<void>{
     this.isAuthenticated.next(false);
     //return Storage.remove({key: TOKEN_KEY});
     return this.storage.remove(TOKEN_KEY);
   }

   getUsuarioLogado(): any{
      return this.USUARIO_LOGADO.value;
   }

   apagaUsuarioLogado(){
    this.USUARIO_LOGADO.next(null);
   }

}
