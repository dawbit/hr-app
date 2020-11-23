import { Component, OnInit } from '@angular/core';
import { TeamMember } from './../../../../classes/team-member';
import * as dawidBitnerJson from './../../../../../../../team/dawidBitner.json';
import * as danielBroczkowskiJson from './../../../../../../../team/danielBroczkowski.json';
import * as mateuszKowolJson from './../../../../../../../team/mateuszKowol.json';

@Component({
  selector: 'app-contact-team',
  templateUrl: './contact-team.component.html',
  styleUrls: ['./contact-team.component.scss']
})
export class ContactTeamComponent implements OnInit {

  constructor() { }
  members: TeamMember[];
  dawidBitner: TeamMember;
  danielBroczkowski: TeamMember;
  mateuszKowol: TeamMember;

  ngOnInit(): void {
    this.dawidBitner = Object.assign(dawidBitnerJson).default;
    this.danielBroczkowski = Object.assign(danielBroczkowskiJson).default;
    this.mateuszKowol = Object.assign(mateuszKowolJson).default;
    this.members = [this.dawidBitner, this.danielBroczkowski, this.mateuszKowol];
  }

}
