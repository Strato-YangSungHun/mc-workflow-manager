import{_ as W,a as I}from"./Tabulator.vue_vue_type_style_index_0_lang-B1UtuJWR.js";import{d as N,P as D,r as L,a as B,b as F,g as P}from"./ParamForm-Cfno5gBf.js";import{d as h,u as v,a as c,b as t,t as x,h as i,c as $,r as w,w as C,k as R,j as g,l as E,F as T,f as V,o as A,m as y,i as b}from"./index-Ds2DFCP7.js";import"./request-qwmp2ebq.js";const G={class:"modal",id:"deleteWorkflow",tabindex:"-1"},j={class:"modal-dialog modal-lg",role:"document"},M={class:"modal-content"},O=t("button",{type:"button",class:"btn-close","data-bs-dismiss":"modal","aria-label":"Close"},null,-1),S=t("div",{class:"modal-status bg-danger"},null,-1),U={class:"modal-body text-left py-4"},q=t("h3",{class:"mb-5"}," Delete Workflow ",-1),z={class:"modal-footer"},H=t("a",{href:"#",class:"btn btn-link link-secondary","data-bs-dismiss":"modal"}," Cancel ",-1),J=h({__name:"DeleteWorkflow",props:{workflowName:{},workflowIdx:{}},emits:["get-workflow-list"],setup(f,{emit:m}){const d=v(),a=f,l=m,s=async()=>{const{data:n}=await N(a.workflowIdx);n?d.success("삭제되었습니다."):d.error("삭제하지 못했습니다."),l("get-workflow-list")};return(n,u)=>(i(),c("div",G,[t("div",j,[t("div",M,[O,S,t("div",U,[q,t("h4",null,"Are you sure you want to delete "+x(a.workflowName)+"?",1)]),t("div",z,[H,t("a",{href:"#",class:"btn btn-primary ms-auto","data-bs-dismiss":"modal",onClick:u[0]||(u[0]=e=>s())}," Delete ")])])])]))}}),K={class:"modal",id:"runWorkflow",tabindex:"-1"},Q={class:"modal-dialog modal-lg",role:"document"},X={class:"modal-content"},Y=t("button",{type:"button",class:"btn-close","data-bs-dismiss":"modal","aria-label":"Close"},null,-1),Z=t("div",{class:"modal-status bg-danger"},null,-1),tt={class:"modal-body text-left py-4"},ot=t("h3",{class:"mb-5"}," Run Workflow ",-1),st={class:"modal-footer"},et=t("a",{href:"#",class:"btn btn-link link-secondary","data-bs-dismiss":"modal"}," Cancel ",-1),at=h({__name:"RunWorkflow",props:{workflowIdx:{}},emits:["get-workflow-list"],setup(f,{emit:m}){const d=v(),a=f,l=m,s=$(()=>a.workflowIdx),n=w({});C(()=>s.value,async()=>{const{data:e}=await B(s.value,"N");n.value=e});const u=async()=>{const{data:e}=await L(n.value);e?d.success("실행되었습니다."):d.error("실행하지 못했습니다."),l("get-workflow-list")};return(e,_)=>(i(),c("div",K,[t("div",Q,[t("div",X,[Y,Z,t("div",tt,[ot,n.value.workflowParams?(i(),R(D,{key:0,popup:!0,"workflow-param-data":n.value.workflowParams,"event-listener-yn":"N"},null,8,["workflow-param-data"])):g("",!0)]),t("div",st,[et,t("a",{href:"#",class:"btn btn-primary ms-auto","data-bs-dismiss":"modal",onClick:_[0]||(_[0]=o=>u())}," Run ")])])])]))}}),lt={class:"modal",id:"workflowLog",tabindex:"-1"},nt={class:"modal-dialog modal-xl",role:"document"},dt={class:"modal-content"},it=t("button",{type:"button",class:"btn-close","data-bs-dismiss":"modal","aria-label":"Close"},null,-1),rt={class:"modal-body text-left py-4"},ct={class:"mb-5"},ut={key:0,class:"spinner-border",role:"status"},wt=t("span",{class:"visually-hidden"},"Loading...",-1),mt=[wt],ft={key:0},_t=t("p",{class:"text-secondary"},"No Data",-1),kt=[_t],bt={class:"card mb-3"},ht=["onClick"],vt={class:"card-title"},pt={key:0,class:"card-body"},gt=["value"],yt=h({__name:"WorkflowLog",props:{workflowIdx:{}},emits:["get-oss-list"],setup(f,{emit:m}){v();const d=f,a=w(!1),l=$(()=>d.workflowIdx);C(l,async()=>{a.value=!1,await n()});const s=w([]),n=async()=>{s.value=[],await F(l.value).then(({data:o})=>{s.value=o,a.value=!0})},u=()=>{s.value=[],e.value=1},e=w(1),_=o=>{e.value===o?e.value=0:e.value=o};return(o,k)=>(i(),c("div",lt,[t("div",nt,[t("div",dt,[it,t("div",rt,[t("h3",ct,[E(" Workflow Log "),a.value?g("",!0):(i(),c("div",ut,mt))]),t("div",null,[s.value.length<=0?(i(),c("div",ft,kt)):(i(!0),c(T,{key:1},V(s.value,r=>(i(),c("div",{key:r.buildIdx},[t("div",bt,[t("div",{class:"card-header",onClick:p=>_(r.buildIdx),style:{cursor:"pointer"}},[t("h3",vt,x(r.buildIdx),1)],8,ht),e.value===r.buildIdx?(i(),c("div",pt,[t("textarea",{value:r.buildLog,disabled:"",style:{width:"100%"},rows:"20"},null,8,gt)])):g("",!0)])]))),128))])]),t("div",{class:"modal-footer"},[t("a",{href:"#",class:"btn btn-link link-secondary","data-bs-dismiss":"modal",onClick:u}," Cancel ")])])])]))}}),xt={class:"card card-flush w-100"},Nt=h({__name:"WorkflowList",setup(f){v();const m=w([]),d=w([]);A(async()=>{n(),await a()});const a=async()=>{try{const{data:o}=await P("N");m.value=o}catch(o){console.log(o)}},l=w(0),s=w(""),n=()=>{d.value=[{title:"Workflow Name",field:"workflowInfo.workflowName",width:500},{title:"Workflow Purpose",field:"workflowInfo.workflowPurpose",width:200},{title:"Params Count",formatter:e,width:400},{title:"Created Date",field:"regDate",width:400},{title:"Action",width:400,formatter:_,cellClick:async(o,k)=>{const r=o.target,p=r==null?void 0:r.getAttribute("id");l.value=k.getRow().getData().workflowInfo.workflowIdx,p==="edit-btn"?y.push("/web/workflow/edit/"+l.value):p==="delete-btn"&&(s.value=k.getRow().getData().workflowInfo.workflowName)}}]},u=()=>{y.push("/web/workflow/new")},e=o=>`<span>${o._cell.row.data.workflowParams.length}</span>`,_=()=>`
    <div>
      <button
        class='btn btn-primary d-none d-sm-inline-block'
        id='edit-btn'
        style='margin-right: 5px'>
          EDIT
      </button>
      <button class='btn btn-danger d-none d-sm-inline-block'
        id='delete-btn'
        data-bs-toggle='modal' 
        data-bs-target='#deleteWorkflow'
        style='margin-right: 5px'>
        DELETE
      </button>
      <button class='btn btn-info d-none d-sm-inline-block'
        id='run-btn'
        data-bs-toggle='modal' 
        data-bs-target='#runWorkflow'>
        RUN
      </button>
      <button class='btn btn-primary d-none d-sm-inline-block'
        id='log-btn'
        data-bs-toggle='modal' 
        data-bs-target='#workflowLog'>
        LOG
      </button>
    </div>`;return(o,k)=>(i(),c("div",xt,[b(I,{"header-title":"Workflow","new-btn-title":"New Workflow",popupFlag:!1,popupTarget:"",onClickNewBtn:u}),b(W,{columns:d.value,"table-data":m.value},null,8,["columns","table-data"]),b(J,{"workflow-name":s.value,"workflow-idx":l.value,onGetWorkflowList:a},null,8,["workflow-name","workflow-idx"]),b(at,{"workflow-idx":l.value,onGetWorkflowList:a},null,8,["workflow-idx"]),b(yt,{"workflow-idx":l.value},null,8,["workflow-idx"])]))}});export{Nt as default};