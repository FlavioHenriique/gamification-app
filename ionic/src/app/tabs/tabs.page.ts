import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'app-tabs',
  templateUrl: 'tabs.page.html',
  styleUrls: ['tabs.page.scss']
})
export class TabsPage {

  constructor(private router: Router,
    private authService: AuthenticationService) {}


  sair(){
      this.authService.apagaUsuarioLogado();
      this.router.navigateByUrl("/login", {replaceUrl:true});
  }
}
