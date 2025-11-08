# mattijs.id

Hetzner hosted

Hetzner cloud instance to trigger the tekton with a public ip address en een ssh public key to automatically setup the following on any ssh server.

<ul>
  <li>Blog
    <ul>
      <li>+bunnyhopcdn all data + migration of current</li>
    </ul>
  </li>
  <li>
    Compiler
    <ul>
      <li>Traefik (compiler-project.ast => mattijs.sh forward without changing url bar name)</li>
    </ul>
  </li>
  <li>
    VPN Wireguard + 2fa 
    <ul>
      <li>Hoarder
        <ul>
          Monitoring stack
          <li>Prometheus</li>
          <li>Grafana</li>
          <li>Alert Manager</li>
        </ul>
        <ul>
          <li>Next cloud (filestorage - keepass data, photos of RAW of camera 5k DSLR, the storage adapter is a hetzner storage box of bucks/month of 10TB via SFTP)</li>
          <li>Guacamole</li>
          <li>Amazon Ec2 instances x7 or 20 with all AV/EDR software a pop to do some malware dev or OSEE stuff through Guacamole</li>
        </ul>
        <ul>
          <li>Rsync.net backups with seperated access credentials</li>
        </ul>
      </li>
  </li>
</ul>
 
compiler
traefik
vpn wireguard+2fa{
  hoarder{
    monitoring stack{
      prometheus, grafana, alert manager
    }, 
    nextcloud{
      filestorage - keepass data, photos of RAW of camera 5k DSLR, the storage adapter is a hetzner storage box of bucks/month of 10TB.
    },
    amazon ec2 instances x7 of 20 with all AV/EDR software a pop to do some malware dev or OSEE stuff.
  }
De compiler wil ik zelf nog wel doen, de rest laten we doen. heb ik wel domein access en die hetzner shit nodig.

Backups on rsync.net with seperated access credentials.



#optional 
https://github.com/fosrl/pangolin bekijken voor wireguard 2fa shizzle
https://github.com/dgtlmoon/changedetection.io/tree/master 
