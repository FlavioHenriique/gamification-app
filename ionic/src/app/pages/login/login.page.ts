import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AlertController, LoadingController } from '@ionic/angular';
import { take } from 'rxjs';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {
  credentials: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthenticationService,
    private alertController: AlertController, 
    private router: Router,
    private loadingController: LoadingController
    ) { }

  ngOnInit() {
    this.credentials = this.fb.group({
      email: ['',[Validators.required, Validators.email]],
      password: ['',[Validators.required, Validators.minLength(6)]]
    })
  }

  async login(){
    const loading = await this.loadingController.create();
    await loading.present();

    this.authService.login(this.credentials.value)
    .pipe(take(1))
    .subscribe(
      async(res) =>{        
        await loading.dismiss();        
        if (this.authService.getUsuarioLogado().visualizouPrimeiraPagina){
          this.router.navigateByUrl('/tabs', {replaceUrl: true});
        }else{
          this.router.navigateByUrl('/apresentacao', {replaceUrl: true});
        }
        
      },
      async(res) =>{
    
        await loading.dismiss();
        const alert = await this.alertController.create({
          header: 'Erro',
          message: res.error.message,
          buttons: ['OK']
        });
        await alert.present();
      }
    );
  }

  cadastro(){
    this.router.navigateByUrl('/cadastro', {replaceUrl: true});
  }

}
