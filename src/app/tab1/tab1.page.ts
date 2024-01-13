import { Component } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';
import { Usuario } from '../entity/Usuario';
import { Tab2Page } from '../tab2/tab2.page';
import { Router } from '@angular/router';

@Component({
  selector: 'app-tab1',
  templateUrl: 'tab1.page.html',
  styleUrls: ['tab1.page.scss']
})
export class Tab1Page {
  component = Tab2Page;
  
  public usuario: Usuario;
  
  constructor(private authService: AuthenticationService,
    private router: Router) {
    this.usuario = authService.getUsuarioLogado();    
  }

  listaInsignias(){
    this.router.navigateByUrl("/insignias", {replaceUrl: true});
  }

}