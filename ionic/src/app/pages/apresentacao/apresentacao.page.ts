import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AlertController } from '@ionic/angular';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UsuarioService } from 'src/app/services/usuario-service';

@Component({
  selector: 'app-apresentacao',
  templateUrl: './apresentacao.page.html',
  styleUrls: ['./apresentacao.page.scss'],
})
export class ApresentacaoPage implements OnInit {

  constructor(private authService: AuthenticationService,
    private usuarioService: UsuarioService,
    private alertController: AlertController,
    private router: Router) { }

  ngOnInit() {
  }

  continuar(){
      this.authService.getUsuarioLogado().visualizouPrimeiraPagina = true;
      this.usuarioService.cadastroUsuario(this.authService.getUsuarioLogado()).subscribe(
        (data) => {
          this.router.navigateByUrl("/tabs", {replaceUrl: true});
        },
        async (data) => {        
        const alert = await this.alertController.create({
          header: 'Erro',
          message: data.error.message,
          buttons: ['OK']
        });  
        await alert.present();
        }
      );
  }

}
