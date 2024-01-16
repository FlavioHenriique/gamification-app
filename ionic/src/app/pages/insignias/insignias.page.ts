import { Component, OnInit } from '@angular/core';
import { InfiniteScrollCustomEvent } from '@ionic/angular';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-insignias',
  templateUrl: './insignias.page.html',
  styleUrls: ['./insignias.page.scss'],
})
export class InsigniasPage implements OnInit {

  insignias = [];
  items = [];

  constructor(private authService: AuthenticationService) { }

  ngOnInit() {
    this.carregaInsignias();
    this.generateItems();
  }

  private carregaInsignias(){
    this.insignias = this.authService.USUARIO_LOGADO.value.insigniasConquistadas;
  }

  private generateItems() {
    const count = this.items.length + 1;
    for (let i = 0; i < 50; i++) {
      this.items.push(`Item ${count + i}`);
    }
  }

  onIonInfinite(ev) {
    this.generateItems();
    setTimeout(() => {
      (ev as InfiniteScrollCustomEvent).target.complete();
    }, 500);
  }
}
