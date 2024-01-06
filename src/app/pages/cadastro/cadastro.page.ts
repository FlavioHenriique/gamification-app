import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AlertController, LoadingController } from '@ionic/angular';
import { UsuarioService } from 'src/app/services/usuario-service';

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.page.html',
  styleUrls: ['./cadastro.page.scss'],
})
export class CadastroPage implements OnInit {

  dadosCadastro: FormGroup;

  constructor(private fb: FormBuilder,
    private usuarioService: UsuarioService,
    private alertController: AlertController,
    private router: Router,
    private loadingController: LoadingController) { 
    
      this.dadosCadastro = this.fb.group({
      nome: ['',[Validators.required, Validators.nullValidator]],
      email: ['',[Validators.required, Validators.email]],
      senha: ['',[Validators.required, Validators.minLength(6)]],
      usuario: ['',[Validators.required, Validators.nullValidator]],
    });
  }

  ngOnInit() {
  }

  async enviaCadastro(){
    const loading = await this.loadingController.create();
    await loading.present();
    console.log(this.dadosCadastro.value.usuario);
    this.usuarioService.cadastroUsuario(this.dadosCadastro.value).subscribe(
      async(res) =>{
        console.log(res);
        loading.dismiss();

        const alert = await this.alertController.create({
          header: 'Sucesso',
          message: "Cadastro do usuário \"" + this.dadosCadastro.value.usuario + "\" concluído",
          buttons: ['OK'] 
        });
        await alert.present();
        this.router.navigateByUrl("/login", {replaceUrl: true});
      },
      async(res) =>{
        loading.dismiss();
        const alert = await this.alertController.create({
          header: 'Erro',
          message: res.error.message,
          buttons: ['OK']
        });
        await alert.present();
      }
    )
  }

}
