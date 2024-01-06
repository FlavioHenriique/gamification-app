import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private httpClient: HttpClient) { }

  cadastroUsuario(dadosCadastro: {nome, email, senha, usuario}): Observable<any>{
    var url = environment.URL_BASE + "/usuario";

    return this.httpClient.post(url, dadosCadastro).pipe(
      map(async(res) =>{              
        return res;
      },
      async(res) =>{          
        throw new Error("erro cadastro");              
      }
    ));          
  }
}
